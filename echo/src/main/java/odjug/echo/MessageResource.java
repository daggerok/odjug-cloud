package odjug.echo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class MessageResource {

    @Autowired
    ExecutorService observableExecutor;

    @Autowired
    ExecutorService completableExecutor;

    @RequestMapping("/ping/{body}")
    public DeferredResult<Message> observable(@PathVariable("body") Optional<String> body) {
        DeferredResult<Message> result = result();

        blah(body).subscribe(message -> result.setResult(message), throwable -> result.setErrorResult(throwable));
        return result;
    }

    @RequestMapping("/block/{body}")
    public Message block(@PathVariable("body") Optional<String> body) {
        return blah(body).toBlocking().first();
    }

    @RequestMapping("/blah/{body}")
    public Observable<Message> blah(@PathVariable("body") Optional<String> body) {
        return Observable.<Message>create(subscriber -> {
            subscriber.onNext(parseBody(body));
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.from(observableExecutor));
    }

    @RequestMapping("/pong/{body}")
    public DeferredResult<Message> completable(@PathVariable("body") Optional<String> body) {
        DeferredResult<Message> result = result();
        blocking(body).whenComplete((message, throwable) -> {
            if (null == throwable) {
                result.setResult(message);
            } else {
                result.setErrorResult(throwable);
            }
        });
        return result;
    }

    @RequestMapping("/puke/{body}")
    public CompletableFuture<Message> blocking(@PathVariable("body") Optional<String> body) {
        return CompletableFuture.supplyAsync(() -> parseBody(body), completableExecutor);
    }

    private DeferredResult<Message> result() {
        return withTimeout(5000L);
    }

    private DeferredResult<Message> withTimeout(long timout) {
        return new DeferredResult<>(timout);
    }

    @SneakyThrows
    Message parseBody(Optional<String> body) {
        log.info("start...");
        TimeUnit.SECONDS.sleep(1);

        Message message = Message.of(body.isPresent() ? body.get() : "holla!");

        log.info("done {}", message);
        return message;
    }
}
