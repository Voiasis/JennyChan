package com.voiasis;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.voiasis.musicstuff.GuildMusicManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.entities.Member;

public class PlayerController extends ListenerAdapter {
  private final AudioPlayerManager playerManager;
  private final Map<Long, GuildMusicManager> musicManagers;

  public String prefix = "!"; //bot prefix

  PlayerController() {
    this.musicManagers = new HashMap<>();

    this.playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
  }

  private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
    long guildId = Long.parseLong(guild.getId());
    GuildMusicManager musicManager = musicManagers.get(guildId);

    if (musicManager == null) {
      musicManager = new GuildMusicManager(playerManager);
      musicManagers.put(guildId, musicManager);
    }

    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

    return musicManager;
  }

  @Override
    public void onMessageReceived(MessageReceivedEvent event) {
      String[] args = event.getMessage().getContentRaw().split(" ");

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.MAGENTA);

      final Member member = event.getMember();
      final User author = event.getAuthor();
      final MessageChannel channel = event.getChannel();
      final Member self = event.getGuild().getSelfMember();
      final GuildVoiceState selfVoiceState = self.getVoiceState();

      String ftr = "Command executed by " + event.getAuthor().getAsTag();
      String avURL = event.getAuthor().getAvatarUrl();

//Music commands

      //join
      if (args[0].equalsIgnoreCase(prefix + "join")) {
        if (member.getVoiceState().getChannel() == null) {
            embed.addField("Error!", "You must be in a voice channel!", false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        } else {
            event.getGuild().getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
            embed.addField("Connected", "Joined the voice channel.", false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
      }
      //leave
      if (args[0].equalsIgnoreCase(prefix + "leave")) {
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() == null) {
            embed.addField("Error!", "I'm not in a voice channel!", false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        } else {
            event.getGuild().getAudioManager().closeAudioConnection();
            embed.addField("Disconnected", "Left the voice channel.", false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
            
        }
      }
      //play
      if (args[0].equalsIgnoreCase(prefix + "play")) {
        try {
            if (args[1].equals(null)) {
            } else {
                event.getGuild().getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
                loadAndPlay(event.getTextChannel(), args[1]);
                embed.setFooter("Command executed by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
            }
        } catch(Exception ex) {
            embed.addField(prefix + "play <music url>", "Plays music.", false);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
      }
      //skip
      if (args[0].equalsIgnoreCase(prefix + "skip")) {
        skipTrack(event.getTextChannel());
      }
      //queue
      if (args[0].equalsIgnoreCase(prefix + "queue")) {
          queue(event.getTextChannel());
      }
      //pause
      if (args[0].equalsIgnoreCase(prefix + "pause")) {
          
      }
      //stop
      if (args[0].equalsIgnoreCase(prefix + "stop")) {
          
      }
  }

  public void loadAndPlay(final TextChannel channel, final String trackUrl) {
    final GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
      @Override
      public void trackLoaded(AudioTrack track) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.addField("Added to queue", track.getInfo().title, false);
        channel.sendMessageEmbeds(embed.build()).queue();
        embed.clear();

        play(channel.getGuild(), musicManager, track);
      }

      @Override
      public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
          firstTrack = playlist.getTracks().get(0);
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.addField("Added to queue", firstTrack.getInfo().title, false);
        embed.addField("From playlist", playlist.getName(), false);
        channel.sendMessageEmbeds(embed.build()).queue();
        embed.clear();

        play(channel.getGuild(), musicManager, firstTrack);
      }

      @Override
      public void noMatches() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.addField("Nothing found from URL", trackUrl, false);
        channel.sendMessageEmbeds(embed.build()).queue();
        embed.clear();
      }

      @Override
      public void loadFailed(FriendlyException exception) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.addField("Could not play track", exception.getMessage(), false);
        channel.sendMessageEmbeds(embed.build()).queue();
        embed.clear();
      }
    });
  }

  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {

    musicManager.scheduler.queue(track);
  }

  public void skipTrack(TextChannel channel) {
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

    musicManager.scheduler.nextTrack();

    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(Color.MAGENTA);
    embed.addField("Skipped to next track", "", false);
    channel.sendMessageEmbeds(embed.build()).queue();
    embed.clear();
  }
  public void queue(TextChannel channel){
    
    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(Color.MAGENTA);
    embed.addField("Track queue", "", false);
    channel.sendMessageEmbeds(embed.build()).queue();
    embed.clear();
  }
}