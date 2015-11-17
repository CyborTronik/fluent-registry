package features;

import com.github.cybortronik.registry.RegistryModule;
import com.github.cybortronik.registry.jwt.JsonKeyLoader;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.google.inject.AbstractModule;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertNotNull;

/**
 * Created by stanislav on 11/17/15.
 */
public class FeaturesModule extends AbstractModule {

    public static final String KEY_FILE = "sample_rsa_public.key";

    @Override
    protected void configure() {
        URL keyUrl = RegistryModule.class.getClassLoader().getResource(KEY_FILE);
        assertNotNull("Public key resource not found for " + KEY_FILE, keyUrl);
        try {
            PublicKey key = new JsonKeyLoader().loadPublicKey(Paths.get(keyUrl.toURI()));
            bind(JwtReader.class).toInstance(new JwtReader(key));
            bindDummyPrivateKey();
        } catch (URISyntaxException | JoseException | IOException e) {
            throw new RuntimeException("Cannot load public key", e);
        }
    }

    private void bindDummyPrivateKey() {
        bind(PrivateKey.class).toInstance(new PrivateKey() {
            @Override
            public String getAlgorithm() {
                return null;
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
        });
    }
}
