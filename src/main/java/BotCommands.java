import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommands extends ListenerAdapter {
    public String prefix = "!"; //bot prefix
    public String botID = "<@!952761165577060453>"; //bot mention ID

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        //mention reply start TODO mention can be used as prefix (with a space ofc)
        if (args[0].equalsIgnoreCase(botID)) {
            event.getMessage().reply("Hey cutie~ ;3" + "\r\n" + "Say \"" + prefix + "commands\" to get started!").queue();
        }
        //mention reply end

        //commands start
        if (args[0].equalsIgnoreCase(prefix + "commands")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("Commands", null);
            embed.setDescription("List of all bot commands.");
            
            embed.addField(prefix + "commands", "Shows this message.", false);
            embed.addField(prefix + "uptime", "Shows the bots current uptime.", false);
            embed.addField(prefix + "ping", "Shows message response time.", false);
            embed.addField(prefix + "say <some text>", "Sends inputted text and deletes command message.", false);
            embed.addField(prefix + "edit <some text>", "Edits bot message you reply to and deletes command message.", false);
            embed.addField(prefix + "delete", "Deletes message you replied to and deletes command message.", false);
            embed.addField(prefix + "message <@user> <some text>", "Sends user a message.", false);
            embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
            embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
            embed.addField(prefix + "kick <@member> [<reason>]", "Kicks a member. Reason is optional.", false);
            embed.addField(prefix + "shutdown", "Shuts down bot.", false);

            embed.setFooter("Bot created by Voiasis#0001", null);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //commands end

        //uptime start TODO add days. show days, hours, minutes, seconds, millis as separate embed fields
        if (args[0].equalsIgnoreCase(prefix + "uptime")) {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long millis = ut % 1000;
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            String uptime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("JennyChan's Uptime", null);
            embed.setDescription("" + uptime);
            embed.setFooter("Bot created by Voiasis#0001", null);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //uptime end

        //ping start TODO fix this properly (time.until(time1, ChronoUnit.MILLIS)
        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            long gw = event.getJDA().getGatewayPing();
            String gwp = Long.toString(gw);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("Pong!", null);
            embed.setDescription("");
            embed.addField("Ping:", "...." + "ms", false);
            embed.addField("Websocket:", gwp + "ms", false);
            embed.setFooter("Bot created by Voiasis#0001", null);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();

            
            

            //event.getMessage().reply("Ping: ... | Websocket: " + event.getJDA().getGatewayPing() + "ms").queue();

            //Temporal m = event.getMessage().getTimeCreated();
           // long ping = event.getMessage().getTimeCreated().until(m, ChronoUnit.MILLIS);
            
            //event.getMessage().editMessage(arg0)
        }
        //ping end

        //say start TODO check for manage messages permission of command user
        if (args[0].equalsIgnoreCase(prefix + "say")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(5)).queue();
                event.getMessage().delete().queue();
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "say <some text>", null);
                embed.setDescription("Sends inputted text.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //say end

        //edit start TODO fix errors when no text is inputted and when replying to wrong message and check for manage messages permission of command user
        if (args[0].equalsIgnoreCase(prefix + "edit")) {
            if (event.getMessage().getContentRaw().toCharArray().length >= 5) {
                if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 1){
                    String msgid = event.getMessage().getMessageReference().getMessageId();
                    event.getChannel().editMessageById(msgid, event.getMessage().getContentRaw().substring(6)).queue();
                    event.getMessage().delete().queue();
                }
            }
            
        }
        //edit end

        //delete start TODO fix error when you don't reply and check for manage messages permission of command user
        if (args[0].equalsIgnoreCase(prefix + "delete")) {
            if (event.getMessage().getMessageReference().getMessageId().toCharArray().length >= 5){
                event.getChannel().deleteMessageById(event.getMessage().getMessageReference().getMessageId()).queue();
                event.getMessage().delete().queue();
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "delete", null);
                embed.setDescription("Deletes message you reply to.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //delete end

        //message start
        if (args[0].equalsIgnoreCase(prefix + "message")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 32) {
                    String msg = event.getMessage().getContentRaw().substring(32);
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(msg)).queue();
                    
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(prefix + "message <@user> <some text>", null);
                    embed.setDescription("Sends user a message.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
                
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "message <@user> <some text>", null);
                embed.setDescription("Sends user a message.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //message end

        //giverole start TODO check for roles permission of command user
        if (args[0].equalsIgnoreCase(prefix + "giverole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToGive = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().addRoleToMember(member, roleToGive).queue();

                    event.getMessage().reply("Gave the role " + roleToGive.getAsMention() + " to " + member.getAsMention() + ".").queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(prefix + "giverole <@role> <@user>", null);
                    embed.setDescription("Gives a user a role.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "giverole <@role> <@user>", null);
                embed.setDescription("Gives a user a role.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //giverole end

        //removerole start TODO check for roles permission of command user
        if (args[0].equalsIgnoreCase(prefix + "removerole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().removeRoleFromMember(member, roleToRemove).queue();

                    event.getMessage().reply("Removed the role " + roleToRemove.getAsMention() + " from " + member.getAsMention() + '.').queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(prefix + "removerole <@role> <@user>", null);
                    embed.setDescription("Removes a role from a user.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "removerole <@role> <@user>", null);
                embed.setDescription("Removes a role from a user.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //removerole end

        //kick start TODO check for kick permission of command user
        if (args[0].equalsIgnoreCase(prefix + "kick")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                if (event.getMessage().getContentRaw().toCharArray().length >= 29) {
                    //kick with reason
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    String reason = event.getMessage().getContentRaw().substring(29);
                    event.getGuild().kick(member, reason).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle("User kicked", null);
                    embed.setDescription(member.getAsMention() + " has been kicked with reason \"" + reason +"\".");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                } else {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    event.getGuild().kick(member, null).queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle("User kicked", null);
                    embed.setDescription(member.getAsMention() + " has been kicked.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "kick <@member>", null);
                embed.setDescription("Kicks a member.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //kick end

        //ban start TODO finish this
        if (args[0].equalsIgnoreCase(prefix + "ban")) {
            //
        }
        //ban end

        //unban start TODO finish this
        if (args[0].equalsIgnoreCase(prefix + "unban")) {
            //
        }
        //unban end

        //shutdown start TODO embed in chat before shutdown. tried this, didnt work. maybe try using a timer of some sort before shutdown.
        if (args[0].equalsIgnoreCase(prefix + "shutdown")) {
            System.exit(1);
        }
        //shutdown end
    }
}