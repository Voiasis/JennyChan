package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AvatarCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        

        if (args[0].equalsIgnoreCase(prefix + "avatar")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if(event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member member = event.getMessage().getMentionedMembers().get(0);
                embed.setTitle("Link", member.getUser().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + member.getUser().getAsMention() + ".");
                embed.setImage(member.getUser().getAvatarUrl() + "?size=1024");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
                
            } else {
                embed.setTitle("Link", event.getAuthor().getAvatarUrl() + "?size=1024");
                embed.setDescription("Avatar of " + event.getAuthor().getAsMention() + ".");
                embed.setImage(event.getAuthor().getAvatarUrl() + "?size=1024");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }    
    }
} //TODO add option for IDs