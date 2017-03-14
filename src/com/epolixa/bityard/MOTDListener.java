package com.epolixa.bityard;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTDListener implements Listener
{
    private final Bityard bityard;

    public MOTDListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onMOTDUpdate(ServerListPingEvent event)
    {
        Sign sign = null;
        if (bityard.getServer().getWorld("world").getBlockAt(543, 56, 837).getType() == Material.WALL_SIGN)
        {
            sign = (Sign)bityard.getServer().getWorld("world").getBlockAt(543, 56, 837).getState();
        }
        if (sign != null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sign.getLine(0));
            for (int i = 1; i < sign.getLines().length; i++)
                stringBuilder.append(" " + sign.getLine(i));
            String signText = stringBuilder.toString();
            event.setMotd(signText);
        }
    }
}
