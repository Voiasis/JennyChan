package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);
            long gw = event.getJDA().getGatewayPing();
            String gwp = Long.toString(gw);
            embed.setTitle("Pong!", null);
            embed.setDescription("");
            embed.addField("Ping:", "...." + "ms", false);
            embed.addField("Websocket:", gwp + "ms", false);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();

            
            

            //event.getMessage().reply("Ping: ... | Websocket: " + event.getJDA().getGatewayPing() + "ms").queue();

            //Temporal m = event.getMessage().getTimeCreated();
           // long ping = event.getMessage().getTimeCreated().until(m, ChronoUnit.MILLIS);
            
            //event.getMessage().editMessage(arg0)
        } 
    }
} //TODO fix this properly (time.until(time1, ChronoUnit.MILLIS)