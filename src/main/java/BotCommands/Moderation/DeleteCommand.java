package BotCommands.Moderation;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeleteCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "delete")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 5){
                event.getChannel().deleteMessageById(event.getMessage().getMessageReference().getMessageId()).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "delete", null);
                embed.setDescription("Deletes message you reply to.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }   
    }
} //TODO fix error when you don't reply and check for manage messages permission of command user