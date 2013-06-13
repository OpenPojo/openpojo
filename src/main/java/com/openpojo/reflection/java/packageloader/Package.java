/*
 * Copyright (c) 2010-2013 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.reflection.java.packageloader;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

/**
 * @author oshoukry
 */
public final class Package {

    @BusinessKey
    private final String packageName;

    public Package(final String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isValid() {
        if (getPackageLoaders().size() > 0) {
            return true;
        }
        return false;
    }

    public Set<Type> getTypes() {
        Set<Type> types = new HashSet<Type>();
        for (PackageLoader packageLoader : getPackageLoaders()) {
            for (Type type : packageLoader.getTypes()) {
                types.add(type);
            }
        }
        return types;
    }

    public Set<Package> getSubPackages() {
        Set<Package> subPackages = new HashSet<Package>();
        Set<String> subPackageNames = new HashSet<String>();
        for (PackageLoader packageLoader : getPackageLoaders()) {
            subPackageNames.addAll(packageLoader.getSubPackages());
        }

        for (String packageName : subPackageNames) {
            subPackages.add(new Package(packageName));
        }

        return subPackages;
    }

    private Set<PackageLoader> getPackageLoaders() {
        Set<PackageLoader> packageLoaders = new HashSet<PackageLoader>();

        Set<URL> resources = PackageLoader.getThreadResources(packageName);
        for (URL resource : resources) {
            packageLoaders.add(PackageLoader.getPackageLoaderByURL(resource, packageName));
        }
        return packageLoaders;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return BusinessIdentity.getHashCode(this);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        return BusinessIdentity.areEqual(this, obj);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
