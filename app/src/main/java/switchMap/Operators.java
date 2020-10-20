package switchMap;

import android.util.Log;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;


public class Operators {
    private static final String TAG = Operators.class.getSimpleName();

    private static final class Producer {
        Observable<String> just() {
            return Observable.just("1", "2", "3", "3");
        }
        Observable<String> just2() {
            return Observable.just("4", "5", "6");
        }
    }

    private static final class Consumer {
        Producer producer;
        Consumer(Producer producer) {
            this.producer = producer;
        }

        public void exec() {
            execFlatMap();
            execSwitchMap();
        }

        public void execFlatMap() {
            producer.just().flatMap(new Function<String, ObservableSource<? extends String>>() {
                @Override
                public ObservableSource<? extends String> apply(String s) throws Throwable {
                    int random = new Random().nextInt(10);
                    return Observable.just(s).delay(random, TimeUnit.MILLISECONDS);
                }
            }).subscribe(
                    (s) -> Log.i(TAG, "flatMap onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete "));
        }
        public void execSwitchMap(){
            producer.just2().switchMap((Function<String, ObservableSource<?>>) s -> {
                int random = new Random().nextInt(10);
                return Observable.just(s).delay(random, TimeUnit.MILLISECONDS);
            }).subscribe((s) -> Log.i(TAG, "switchMap onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete "));
            /*
            switchMap - Как видно из результата, мы получаем единичный observable содержащий последний переданный элемент из source Observable.
            Таким образом, как только он получил новый элемент, он отписывается от предыдущего и подписывается на новый.
            flatMap - хранит все результаты и возвращает их в произвольном порядке

            Вывод: -Если нам не важен порядом появления элементов и нам нужны все элементы,а так же их появление как можно скорее, то FlatMap - наш выбор.
                   -Если нам нучто что-то заменять и предущий элемент больше не нужен, то берем switchMap(как вариант поиск)
                   -Если нам нужны все элементы и важна последовательность, то берем concatMap, но он медленный. Так как он не подпишется на следующий Observable пока
                       не завершится(получим) предыдущий.
             */

        }
    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }
}
