package com.redpacket.server.common;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see https://spring.io/guides/gs/consuming-rest/
 * @author Liu.D.H
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocoderResult {

    private String status;
    private String message;
    
	@JsonProperty("result")
    private Result result;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	
	@Override
	public String toString() {
		return "GeocoderResult [status=" + status + ", message=" + message + ", result=" + result + "]";
	}


	@JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
		
		@JsonProperty("address_component")
    	private AddressComponent addressComponent;

		public AddressComponent getAddressComponent() {
			return addressComponent;
		}

		public void setAddressComponent(AddressComponent addressComponent) {
			this.addressComponent = addressComponent;
		}

		@Override
		public String toString() {
			return "Result [addressComponent=" + addressComponent + "]";
		}
    	
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressComponent {
    	private String nation;
    	private String province;
    	private String city;
    	private String district;
    	private String street;
    	private String street_number;
    	
		public String getNation() {
			return nation;
		}
		public void setNation(String nation) {
			this.nation = nation;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getStreet_number() {
			return street_number;
		}
		public void setStreet_number(String street_number) {
			this.street_number = street_number;
		}
		@Override
		public String toString() {
			return "AddressComponent [nation=" + nation + ", province=" + province + ", city=" + city + ", district="
					+ district + ", street=" + street + ", street_number=" + street_number + "]";
		}
    	
    }
}