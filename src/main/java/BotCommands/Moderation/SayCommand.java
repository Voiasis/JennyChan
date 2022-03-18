package BotCommands.Moderation;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SayCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        

        if (args[0].equalsIgnoreCase(prefix + "say")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(5)).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "say <some text>", null);
                embed.setDescription("Sends inputted text.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }  
    }
} //TODO check for manage messages permission of command user