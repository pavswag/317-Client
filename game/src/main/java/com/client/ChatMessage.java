package com.client;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.runelite.api.ChatMessageType;
import net.runelite.api.MessageNode;
import net.runelite.api.Node;

import java.time.Instant;

@ToString
@EqualsAndHashCode
public class ChatMessage implements MessageNode {
    private static int chatUid = 0;
    public ChatMessage() {
        this.uid = chatUid++;
    }
    private static int chatId = 0;
    public long chatTime;
    public int type;
    private String playerName;
    private String sender;
    private String message;
    private String runeliteFormattedMessage;
    private String clanTitle = Client.instance.clanTitle;
    private int time = Instant.now().getNano();
    public int uid;
    public boolean hovered = false;
    @Override
    public int getId() {
        return chatId++;
    }

    @Override
    public ChatMessageType getType() {
        return ChatMessageType.of(type);
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public void setName(String name) {
        this.playerName = name;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getValue() {
        return message;
    }

    public String getMessage() {
        if(runeliteFormattedMessage != null && !runeliteFormattedMessage.isEmpty())
            return runeliteFormattedMessage;
        return getValue();
    }

    @Override
    public void setValue(String value) {
        this.message = value;
    }

    @Override
    public String getRuneLiteFormatMessage() {
        return runeliteFormattedMessage;
    }

    @Override
    public void setRuneLiteFormatMessage(String runeLiteFormatMessage) {
//        System.out.println("runelite message before = " + getValue());
        this.runeliteFormattedMessage = runeLiteFormatMessage;
//        System.out.println("runelite message after = " + getMessage());
    }

    @Override
    public int getTimestamp() {
        return time;
    }

    @Override
    public void setTimestamp(int timestamp) {
        this.time = timestamp;
    }

    @Override
    public boolean isFromFriend() {
        long l6 = StringUtils.longForName(playerName);
        for (int k6 = 0; k6 < Client.instance.friendsCount; k6++) {
            if (l6 != Client.instance.friendsListAsLongs[k6] || Client.instance.friendsNodeIDs[k6] == 0)
                continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFromClanMate() {
        String clanname = null;
        for (int j3 = 0; j3 < Client.instance.clanList.length; j3++) {
            if (Client.instance.clanList[j3] == null)
                continue;
            clanname = Client.instance.clanList[j3];
            if (clanname.startsWith("<clan"))
                clanname = clanname.substring(clanname.indexOf(">") + 1, clanname.length());
            if (!clanname.equalsIgnoreCase(getName()))
                continue;
            return true;
        }
        return false;
    }

    @Override
    public Node getNext() {
        return null;
    }

    @Override
    public Node getPrevious() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public long getHash() {
        return 0;
    }
}
