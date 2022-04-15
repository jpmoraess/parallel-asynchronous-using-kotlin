package br.com.moraesit.completablefuture;

import br.com.moraesit.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
}
