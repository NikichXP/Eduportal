package eduportal.dao.entity;

import java.util.*;
import com.googlecode.objectify.*;
import com.googlecode.objectify.annotation.*;

@Entity
public class Permission extends AbstractEntity {
	
	/** @see AccessSettings.class */
	private int accessGroup; //is a accessGroup
	private HashSet<Long> country;
	private HashSet<Long> city;
	@Index
	private Key<Corporation> corporation;
	
	public Permission () {
		super();
		accessGroup = 0;
		country = new HashSet<>();
		city = new HashSet<>();
	}
	
	public void addCity (City c) {
		city.add(Ref.create(c).getKey().getId());
	}
	
	public void addCountry (Country c) {
		country.add(Ref.create(c).getKey().getId());
	}

	public HashSet<Long> getCountry() {
		return country;
	}

	public HashSet<Long> getCity() {
		return city;
	}

	public long getCorporation() {
		return corporation.getId();
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = Ref.create(corporation).getKey();
	}
	
	public Corporation corporationEntity () {
		return Ref.create(corporation).get();
	}

	public void setCountry(HashSet<Long> country) {
		this.country = country;
	}

	public void setCity(HashSet<Long> city) {
		this.city = city;
	}
	
	public void addCountrySet (Set<Country> set) {
		for (Country c : set) {
			country.add(c.getId());
		}
	}
	
	public void addCitySet (Set<City> set) {
		for (City c : set) {
			city.add(c.getId());
		}
	}
	
	public int getAccessGroup() {
		return accessGroup;
	}

	public void setAccessGroup(int accessGroup) {
		this.accessGroup = accessGroup;
	}

	@Override
	public String toString() {
		return "Permission [category=" + accessGroup + ", country=" + country + ", city=" + city + ", orgName=" + corporation
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accessGroup;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((corporation == null) ? 0 : corporation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Permission)) {
			return false;
		}
		Permission other = (Permission) obj;
		if (accessGroup != other.accessGroup) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (corporation == null) {
			if (other.corporation != null) {
				return false;
			}
		} else if (!corporation.equals(other.corporation)) {
			return false;
		}
		return true;
	}	
}
