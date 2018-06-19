package com.example.ryanberry.popularmovies;

        import android.arch.lifecycle.LiveData;
        import android.arch.lifecycle.ViewModel;

        import com.example.ryanberry.popularmovies.model.AppDatabase;
        import com.example.ryanberry.popularmovies.model.PopularMovie;

        import java.util.List;

public class AddMovieViewModel extends ViewModel {

    private LiveData<List<PopularMovie>> popularMovieLiveData;

    public AddMovieViewModel(AppDatabase database, int movieID) {
        popularMovieLiveData = database.movieDOA().loadAllTask();
    }

    public LiveData<List<PopularMovie>> getPopularMovieLiveData() {
        return popularMovieLiveData;
    }


}
