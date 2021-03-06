package hugu1026.com.github.phantasymagic.magic;

import hugu1026.com.github.phantasymagic.event.ActivateMagicEvent;
import hugu1026.com.github.phantasymagic.event.PlayerManaChangeEvent;
import hugu1026.com.github.phantasystatus.util.PlayerDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.io.File;

public abstract class Magic {
    private Location magicLocation;
    private int magicPower;

    public Magic(String magicName, Event event, Integer slot, Integer neededMana, Player player) {
        if (checkMagicName(magicName, player) && checkActivateEventName(event)) {
            ActivateMagicEvent activateMagicEvent = (ActivateMagicEvent) event;
            magicPower = PlayerDataUtil.getPlayerMAGIC(activateMagicEvent.getPlayer());

            if (checkMana(getPlayerMana(activateMagicEvent.getPlayer()), neededMana)) {
                magicLocation = this.getMagicLocation(slot, (activateMagicEvent.getPlayer().getLocation()));

                ActivatedMagic(activateMagicEvent, magicLocation, magicPower);
                consumeMana(activateMagicEvent.getPlayer(), neededMana);
            } else {
                activateMagicEvent.getPlayer().sendMessage(ChatColor.RED + "マナが足りない！");
            }
        }
    }

    public abstract void ActivatedMagic(ActivateMagicEvent event, Location magicLocation, int magicPower);

    public abstract boolean checkMagicName(String magicName, Player player);

    public boolean checkActivateEventName(Event event) {
        if (event instanceof ActivateMagicEvent) {
            return true;
        } else {
            return false;
        }
    }

    public Location getMagicLocation(int slot, Location playerLocation) {
        int addBlockstoForward, addBlockstoSide;
        switch (slot) {
            //how many blocks to extend forward
            //this means that the negative number extends backward
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                addBlockstoForward = 4;
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                addBlockstoForward = 3;
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                addBlockstoForward = 2;
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                addBlockstoForward = 1;
                break;
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
                addBlockstoForward = 0;
                break;
            default:
                return null;
        }
        switch (slot) {
            //how many blocks to extend to the right
            //this means that the negative number extends to the left
            case 2:
            case 11:
            case 20:
            case 29:
            case 38:
                addBlockstoSide = -2;
                break;
            case 3:
            case 12:
            case 21:
            case 30:
            case 39:
                addBlockstoSide = -1;
                break;
            case 4:
            case 13:
            case 22:
            case 31:
            case 40:
                addBlockstoSide = 0;
                break;
            case 5:
            case 14:
            case 23:
            case 32:
            case 41:
                addBlockstoSide = 1;
                break;
            case 6:
            case 15:
            case 24:
            case 33:
            case 42:
                addBlockstoSide = 2;
                break;
            default:
                return null;
        }

        Vector vector = playerLocation.getDirection();
        Location originalClone = playerLocation.clone();

        //extend vector forward
        Vector vector1 = vector.multiply(addBlockstoForward);
        //rotate vector clockwise
        originalClone.setYaw(originalClone.getYaw() + 90);
        //extend vector to the right
        Vector vector2 = originalClone.getDirection().multiply(addBlockstoSide);

        //add two vectors to player's vector
        this.magicLocation = playerLocation.add(vector1).add(vector2);

        return this.magicLocation;
    }

    public int getPlayerMana(Player player) {
        return PlayerDataUtil.getPlayerMANA(player);
    }

    public boolean checkMana(int playerHavingMana, int neededMana) {
        return playerHavingMana >= neededMana;
    }

    public void consumeMana(Player player, int neededMana) {
        File playerFile = PlayerDataUtil.getPlayerFile(player);
        FileConfiguration playerData = PlayerDataUtil.getPlayerData(player);

        int originalMana = PlayerDataUtil.getPlayerMANA(player);

        playerData.set("status.mana", originalMana - neededMana);
        PlayerDataUtil.savePlayerData(playerFile, playerData, player);

        PlayerManaChangeEvent manaChangeEvent = new PlayerManaChangeEvent(player, originalMana, -neededMana);
        Bukkit.getServer().getPluginManager().callEvent(manaChangeEvent);
    }

    public double magicDamageCalc(int base, int magicPower) {
        return (double) base + (magicPower * 0.5);
    }
}
