package ir.bppir.pishtazan.daggers.realm;

import dagger.Component;
import io.realm.Realm;
import ir.bppir.pishtazan.daggers.DaggerScope;

@DaggerScope
@Component(modules = {RealmModul.class})
public interface RealmComponent {
    Realm getRealm();
}