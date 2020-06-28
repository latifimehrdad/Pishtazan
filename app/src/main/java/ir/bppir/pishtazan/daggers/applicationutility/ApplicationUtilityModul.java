package ir.bppir.pishtazan.daggers.applicationutility;


import dagger.Module;
import dagger.Provides;
import ir.bppir.pishtazan.utility.ApplicationUtility;

@Module
public class ApplicationUtilityModul {
    @Provides
    public ApplicationUtility getApplicationUtility() {
        return new ApplicationUtility();
    }
}