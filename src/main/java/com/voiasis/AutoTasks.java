package com.voiasis;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoTasks extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getId().equals("927712748488507433")) {
            event.getMessage().createThreadChannel("[" + event.getAuthor().getName() + "] Suggestion Discussion").queue();
        }
    }
}