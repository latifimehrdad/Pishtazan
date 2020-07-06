package ir.bppir.pishtazan.daggers.imageloader;

import com.nostra13.universalimageloader.core.ImageLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageLoaderModule {

    @Provides
    public ImageLoader getImageLoader(){
        return ImageLoader.getInstance();
    }
}