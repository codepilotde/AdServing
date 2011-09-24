/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.manager.web.theme;

import java.io.Serializable;

import org.apache.wicket.request.resource.ResourceReference;



/**
 * 
 *
 */
public abstract class UITheme implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;
        
        /**
         * Constructor
         */
        public UITheme(String name) {
                this.name = name;
        }

        public abstract ResourceReference getTheme();

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
        
        @Override
        public String toString() {
                return getName();
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((name == null) ? 0 : name.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                UITheme other = (UITheme) obj;
                if (name == null) {
                        if (other.name != null)
                                return false;
                } else if (!name.equals(other.name))
                        return false;
                return true;
        }
        
        
}