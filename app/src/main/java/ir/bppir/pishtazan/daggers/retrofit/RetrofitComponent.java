package ir.bppir.pishtazan.daggers.retrofit;


import dagger.Component;
import ir.bppir.pishtazan.daggers.DaggerScope;

@DaggerScope
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    RetrofitApiInterface getRetrofitApiInterface();
}
