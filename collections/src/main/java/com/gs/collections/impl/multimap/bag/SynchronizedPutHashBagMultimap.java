/*
 * Copyright 2014 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.multimap.bag;

import java.io.Externalizable;

import com.gs.collections.api.bag.ImmutableBag;
import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.Multimap;
import com.gs.collections.api.multimap.bag.ImmutableBagMultimap;
import com.gs.collections.api.multimap.bag.MutableBagMultimap;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.bag.mutable.HashBag;
import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import com.gs.collections.impl.map.mutable.UnifiedMap;
import com.gs.collections.impl.multimap.AbstractSynchronizedPutMultimap;
import com.gs.collections.impl.utility.ArrayIterate;
import com.gs.collections.impl.utility.Iterate;

/**
 * A Multimap that is optimized for parallel writes, but is not protected for concurrent reads.
 */
public final class SynchronizedPutHashBagMultimap<K, V>
        extends AbstractSynchronizedPutMultimap<K, V, MutableBag<V>> implements MutableBagMultimap<K, V>, Externalizable
{
    private static final long serialVersionUID = 42L;

    public SynchronizedPutHashBagMultimap()
    {
    }

    public SynchronizedPutHashBagMultimap(int initialCapacity)
    {
        super(ConcurrentHashMap.<K, MutableBag<V>>newMap(initialCapacity));
    }

    public SynchronizedPutHashBagMultimap(Multimap<? extends K, ? extends V> multimap)
    {
        this.putAll(multimap);
    }

    public SynchronizedPutHashBagMultimap(Pair<K, V>... pairs)
    {
        ArrayIterate.forEach(pairs, new Procedure<Pair<K, V>>()
        {
            public void value(Pair<K, V> pair)
            {
                SynchronizedPutHashBagMultimap.this.put(pair.getOne(), pair.getTwo());
            }
        });
    }

    public SynchronizedPutHashBagMultimap(Iterable<Pair<K, V>> inputIterable)
    {
        Iterate.forEach(inputIterable, new Procedure<Pair<K, V>>()
        {
            public void value(Pair<K, V> pair)
            {
                SynchronizedPutHashBagMultimap.this.add(pair);
            }
        });
    }

    public static <K, V> SynchronizedPutHashBagMultimap<K, V> newMultimap()
    {
        return new SynchronizedPutHashBagMultimap<K, V>();
    }

    public static <K, V> SynchronizedPutHashBagMultimap<K, V> newMultimap(int initialCapacity, float loadFactor, int concurrencyLevel)
    {
        return new SynchronizedPutHashBagMultimap<K, V>(initialCapacity);
    }

    public static <K, V> SynchronizedPutHashBagMultimap<K, V> newMultimap(Multimap<? extends K, ? extends V> multimap)
    {
        return new SynchronizedPutHashBagMultimap<K, V>(multimap);
    }

    public static <K, V> SynchronizedPutHashBagMultimap<K, V> newMultimap(Pair<K, V>... pairs)
    {
        return new SynchronizedPutHashBagMultimap<K, V>(pairs);
    }

    public static <K, V> SynchronizedPutHashBagMultimap<K, V> newMultimap(Iterable<Pair<K, V>> inputIterable)
    {
        return new SynchronizedPutHashBagMultimap<K, V>(inputIterable);
    }

    @Override
    protected MutableBag<V> createCollection()
    {
        return HashBag.newBag(1);
    }

    public SynchronizedPutHashBagMultimap<K, V> newEmpty()
    {
        return new SynchronizedPutHashBagMultimap<K, V>();
    }

    public MutableBagMultimap<K, V> toMutable()
    {
        return new SynchronizedPutHashBagMultimap<K, V>(this);
    }

    public ImmutableBagMultimap<K, V> toImmutable()
    {
        final MutableMap<K, ImmutableBag<V>> map = UnifiedMap.newMap();

        this.map.forEachKeyValue(new Procedure2<K, MutableBag<V>>()
        {
            public void value(K key, MutableBag<V> bag)
            {
                map.put(key, bag.toImmutable());
            }
        });

        return new ImmutableBagMultimapImpl<K, V>(map);
    }

    public HashBagMultimap<K, V> selectKeysValues(Predicate2<? super K, ? super V> predicate)
    {
        return this.selectKeysValues(predicate, HashBagMultimap.<K, V>newMultimap());
    }

    public HashBagMultimap<K, V> rejectKeysValues(Predicate2<? super K, ? super V> predicate)
    {
        return this.rejectKeysValues(predicate, HashBagMultimap.<K, V>newMultimap());
    }

    public HashBagMultimap<K, V> selectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate)
    {
        return this.selectKeysMultiValues(predicate, HashBagMultimap.<K, V>newMultimap());
    }

    public HashBagMultimap<K, V> rejectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate)
    {
        return this.rejectKeysMultiValues(predicate, HashBagMultimap.<K, V>newMultimap());
    }

    public <K2, V2> HashBagMultimap<K2, V2> collectKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function)
    {
        return this.collectKeysValues(function, HashBagMultimap.<K2, V2>newMultimap());
    }

    public <V2> HashBagMultimap<K, V2> collectValues(Function<? super V, ? extends V2> function)
    {
        return this.collectValues(function, HashBagMultimap.<K, V2>newMultimap());
    }

    public MutableBagMultimap<V, K> flip()
    {
        return Iterate.flip(this);
    }

    public void putOccurrences(K key, V value, int occurrences)
    {
        if (occurrences < 0)
        {
            throw new IllegalArgumentException("Cannot add a negative number of occurrences");
        }

        if (occurrences > 0)
        {
            MutableBag<V> bag = this.map.getIfAbsentPutWith(key, this.createCollectionBlock(), this);

            synchronized (bag)
            {
                bag.addOccurrences(value, occurrences);
                this.addToTotalSize(occurrences);
            }
        }
    }
}
