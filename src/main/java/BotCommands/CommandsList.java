package BotCommands;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandsList extends ListenerAdapter {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);

        if (args[0].equalsIgnoreCase(prefix + "commands")) {
            embed.setTitle("Commands. Page 1 of 2.", null);
            embed.setDescription("");
            
            embed.addField(prefix + "avatar [<@user>]", "Shows avatar of user.", false);
            embed.addField(prefix + "react <emoji>", "Adds reaction to replied message.", false);
            embed.addField(prefix + "say <some text>", "Sends text.", false);
            embed.addField(prefix + "reply <some text>", "Replys to replied message.", false);
            embed.addField(prefix + "edit <some text>", "Edits bot message you reply to.", false);
            embed.addField(prefix + "delete", "Deletes message you replied to.", false);
            embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
            embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
            embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
            embed.addField(prefix + "kick <@user> [<reason>]", "Kicks a user. Reason is optional.", false);

            embed.addField("","Use \"" + prefix + "commands2\" to go to page 2.", false);
            embed.setFooter(ftr, avURL);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        } 

        if (args[0].equalsIgnoreCase(prefix + "commands2")) {
            embed.setTitle("Commands. Page 2 of 2", null);
            embed.setDescription("");

            embed.addField(prefix + "userinfo", "Shows users info.", false);
            embed.addField(prefix + "serverinfo", "Shows server info.", false);
            embed.addField(prefix + "uptime", "Shows the bots uptime.", false);
            embed.addField(prefix + "ping", "Shows message response time.", false);
            embed.addField(prefix + "shutdown", "Shuts down bot.", false);
            embed.addField(prefix + "commands", "Lists page 1 of commands.", false);
            embed.addField(prefix + "commands2", "Lists page 2 of commands.", false);

            embed.addField("","Use \"" + prefix + "commands\" to go to page 1.", false);
            embed.setFooter(ftr, avURL);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        } 
    }
}
//TODO add emoji page system thing