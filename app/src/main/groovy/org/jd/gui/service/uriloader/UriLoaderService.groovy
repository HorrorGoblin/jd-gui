/*
 * Copyright (c) 2008-2015 Emmanuel Dupuy
 * This program is made available under the terms of the GPLv3 License.
 */

package org.jd.gui.service.uriloader

import org.jd.gui.api.API
import org.jd.gui.service.extension.ExtensionService
import org.jd.gui.spi.UriLoader

@Singleton(lazy = true)
class UriLoaderService {
    protected final Collection<UriLoader> providers = ExtensionService.instance.load(UriLoader)
	
	protected Map<String, UriLoader> mapProviders = providers.collectEntries { provider ->
		provider.schemes.collectEntries { [it, provider] }
	}

    UriLoader get(API api, URI uri) {
        def provider = mapProviders[uri.scheme]

        if (provider.accept(api, uri)) {
            return provider
        } else {
            return null
        }
    }
}
