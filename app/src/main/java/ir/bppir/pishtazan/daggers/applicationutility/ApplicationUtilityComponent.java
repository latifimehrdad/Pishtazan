package ir.bppir.pishtazan.daggers.applicationutility;

import dagger.Component;
import ir.bppir.pishtazan.utility.ApplicationUtility;

@Component(modules = {ApplicationUtilityModul.class})
public interface ApplicationUtilityComponent {
    ApplicationUtility getApplicationUtility();
}