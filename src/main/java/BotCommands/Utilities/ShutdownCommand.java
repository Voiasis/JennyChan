package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShutdownCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setFooter(ftr, avURL);

        if (args[0].equalsIgnoreCase(prefix + "shutdown")) {
            

            String authorID = event.getAuthor().getId();
            if (authorID != "472899069136601099");
            
            System.exit(1);
        } else{
            embed.setTitle("Error", null);
            embed.setDescription("You are not the owner of the bot!");
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }  
    }
} //TODO embed in chat before shutdown. tried this, didnt work. maybe try using a timer of some sort before shutdown.