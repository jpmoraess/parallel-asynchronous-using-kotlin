package br.com.moraesit.completablefuture;

import br.com.moraesit.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException cfwe;

    @Test
    void helloworld_3_async_calls_handle() {
        // given
        lenient().when(helloWorldService.hiCompletableFuture()).thenThrow(new RuntimeException("Exception Ocurred"));
        lenient().when(helloWorldService.hello()).thenCallRealMethod();

        // when
        final String result = cfwe.helloworld_3_async_calls_handle();

        // then
        assertEquals("HELLO WORLD!", result);
    }

    @Test
    void helloworld_3_async_calls_handle_2() {
        // given
        lenient().when(helloWorldService.hiCompletableFuture()).thenThrow(new RuntimeException("Exception Ocurred"));
        lenient().when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Ocurred"));

        // when
        final String result = cfwe.helloworld_3_async_calls_handle();

        // then
        assertEquals("WORLD!", result);
    }

    @Test
    void helloworld_3_async_calls_handle_3() {
        // given
        lenient().when(helloWorldService.hiCompletableFuture()).thenCallRealMethod();
        lenient().when(helloWorldService.hello()).thenCallRealMethod();

        // when
        final String result = cfwe.helloworld_3_async_calls_handle();

        // then
        assertEquals("HI COMPLETABLEFUTURE HELLO WORLD!", result);
    }
}
