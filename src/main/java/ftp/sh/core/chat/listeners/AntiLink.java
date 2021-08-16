package ftp.sh.core.chat.listeners;

import ftp.sh.core.chat.ChatManager;
import ftp.sh.core.event.CustomEventHandler;
import ftp.sh.core.event.Listener;
import ftp.sh.core.event.events.PacketEvent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

import java.lang.reflect.Field;
import java.util.List;

public class AntiLink implements Listener {
    private final ChatManager manager;
    private Field messageF;

    public AntiLink(ChatManager manager) {
        this.manager = manager;
        try {
            messageF = PacketPlayOutChat.class.getDeclaredField("a");
            messageF.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @CustomEventHandler
    public void onPacket(PacketEvent.Outgoing event) {
        if (event.getPacket() instanceof PacketPlayOutChat) {
            try {
                PacketPlayOutChat packet = (PacketPlayOutChat) event.getPacket();
                IChatBaseComponent message = (IChatBaseComponent) messageF.get(packet);
                List<String> list = manager.getConfig().getConfig().getStringList("Blocked");
                for (String word : list) {
                    if (message.toPlainText().contains(word)) {
                        event.setCancelled(true);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
