package ir.bppir.pishtazan.daggers.realm;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import ir.bppir.pishtazan.daggers.DaggerScope;

@Module
public class RealmModul {

    @Provides
    @DaggerScope
    public Realm getRealm() {//_____________________________________________________________________ Start getRealm
        return Realm.getDefaultInstance();
    }//_____________________________________________________________________________________________ End getRealm
}