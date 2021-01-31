package sd;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class Db extends PluginBase {

    private Config config;

    @Override
    public void onLoad() {
        config = new Config("plugins/SDB/config.yml", Config.YAML);
    }

    @Override
    public void onEnable(){
        this.getServer().getLogger().info("Плагин включен!'");
    }

    public Config getDb(){
        return config;
    }

    public boolean newPlayer(Player player){
        String playerName = player.getName().toLowerCase();
        if(!this.checkPlayer(player)){
            this.config.set(playerName + ".nick", playerName);
            this.config.set(playerName + ".job", 0);
            this.config.set(playerName + ".job_exp", 0);
            this.config.set(playerName + ".job_level", 1);
            this.config.set(playerName + ".money", 10000);
            this.config.set(playerName + ".kills", 0);
            this.config.set(playerName + ".deaths", 0);
            this.config.set(playerName + ".clan", "null");
            this.config.save();
            this.config.reload();
            this.getServer().getLogger().info("Профиль нового игрока успешно создан!");
        }
        return false;
    }

    public boolean checkPlayer(Player player){
        boolean exists = this.config.exists(player.getName().toLowerCase() + ".nick");
        if (exists) return true; // если есть, то тру
        else return false; // если нет, то фолс
    }
    public String getStringColumn(Player player, String column){
        if(!this.checkPlayer(player)) return "Загрузка..."; // в россии живём))
        String s = this.config.getString(player.getName().toLowerCase() + "." + column);
        if(s == null) return "Загрузка..."; // ачё)))))
        return s;
    }
    public boolean set(Player player, String column, String value){
        try {
            this.config.set(player.getName().toLowerCase() + "." + column, value);
            this.config.save();
            this.config.reload();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
