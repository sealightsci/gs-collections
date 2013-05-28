/*
 * Copyright 2013 Goldman Sachs.
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

package com.gs.collections.impl.map.mutable.primitive;

import com.gs.collections.impl.test.Verify;
import org.junit.Test;

public class SynchronizedCharShortMapTest
{
    @Test
    public void serializedForm()
    {
        Verify.assertSerializedForm(
                1L,
                "rO0ABXNyAEZjb20uZ3MuY29sbGVjdGlvbnMuaW1wbC5tYXAubXV0YWJsZS5wcmltaXRpdmUuU3lu\n"
                        + "Y2hyb25pemVkQ2hhclNob3J0TWFwAAAAAAAAAAECAAJMAARsb2NrdAASTGphdmEvbGFuZy9PYmpl\n"
                        + "Y3Q7TAADbWFwdAA6TGNvbS9ncy9jb2xsZWN0aW9ucy9hcGkvbWFwL3ByaW1pdGl2ZS9NdXRhYmxl\n"
                        + "Q2hhclNob3J0TWFwO3hwcQB+AANzcgA+Y29tLmdzLmNvbGxlY3Rpb25zLmltcGwubWFwLm11dGFi\n"
                        + "bGUucHJpbWl0aXZlLkNoYXJTaG9ydEhhc2hNYXAAAAAAAAAAAQwAAHhwdwgAAAAAPwAAAHg=",
                new SynchronizedCharShortMap(new CharShortHashMap()));
    }
}
