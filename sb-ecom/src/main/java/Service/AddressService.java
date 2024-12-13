package Service;

public interface AddressService {

    public Address saveAddress();
    public List<Address> getAddressByUserId(Integer userId);
    public String deleteAddress(Integer userId, Integer addressId);
}
