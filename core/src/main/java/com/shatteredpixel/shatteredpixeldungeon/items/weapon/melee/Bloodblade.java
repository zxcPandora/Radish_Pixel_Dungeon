package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.lang.model.util.AbstractAnnotationValueVisitor6;

public class Bloodblade extends MeleeWeapon{
    private static ItemSprite.Glowing RED = new ItemSprite.Glowing( 0x660022 );
    public static final String AC_SAC	= "SAC";
    public int sac=0;
    {
        image = ItemSpriteSheet.LONGSWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        tier = 4;
        defaultAction= AC_SAC;
    }
    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_SAC );
        return actions;
    }
    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_SAC )) {
            if (!isEquipped( hero )){
                GLog.w(Messages.get(this,"not_equipped"));
            }
            else if (Dungeon.hero.HT<=5){
                GLog.w(Messages.get(this,"low_ht"));
            }
            else {
                Dungeon.hero.HTBoost-=5;
                hero.updateHT(false);
                sac+=3;
                GLog.i(Messages.get(this,"sac"));
            }
        }
    }
    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //20 base, down from 25
                lvl*(tier+1)   //scaling unchanged
                +sac;
    }
    @Override
    public ItemSprite.Glowing glowing() {
        return RED;
    }
    @Override
    public String statsInfo() {
        String d=Messages.get(this, "stats_desc");
        d+=Messages.get(this,"cnt",new DecimalFormat("#").format(sac/3));
        return d;
    }
    private static final String SACR = "sacr";
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        sac = bundle.getInt(SACR);
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(SACR, sac);
    }
}
