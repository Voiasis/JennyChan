package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReactCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        

        if (args[0].equalsIgnoreCase(prefix + "react")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                String emoji1 = event.getMessage().getContentRaw().substring(9);
                String emoji2 = emoji1.replaceFirst(">","");
                Message msg = event.getMessage().getMessageReference().getMessage();
                msg.addReaction(emoji2).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "react <emoji>", null);
                embed.setDescription("Adds reaction to replied message.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }  
    }
}