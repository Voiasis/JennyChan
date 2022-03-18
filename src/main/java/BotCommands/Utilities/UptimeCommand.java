package BotCommands.Utilities;
import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UptimeCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        
        
        if (args[0].equalsIgnoreCase(prefix + "uptime")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long millis = ut % 1000;
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            String uptime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
            embed.setTitle("JennyChan's Uptime", null);
            embed.setDescription("" + uptime);
            
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }
} //TODO add days. show days, hours, minutes, seconds, millis as separate embed fields