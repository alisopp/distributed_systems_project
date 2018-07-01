package com.distributed_systems.group_2;

import java.util.ArrayList;

public class ChatHistory {

    private ArrayList<String> history;

    public ChatHistory() {
        history = new ArrayList<>();
    }

    public String getCompleteHistory()
    {
        StringBuilder builder = new StringBuilder();
        for (String entry :
                history) {
            builder.append(entry).append("\n");
        }
        return builder.toString();
    }


    public void addInput(String input)
    {
        history.add(input);
    }
}
