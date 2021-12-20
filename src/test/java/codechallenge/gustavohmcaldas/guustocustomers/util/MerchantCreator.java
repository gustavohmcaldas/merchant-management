package codechallenge.gustavohmcaldas.guustocustomers.util;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;

public class MerchantCreator {
	
	public static Merchant createValidMerchant() {
		return new Merchant("Amazon", "Canada", "https://amazon.ca", "http://images.guusto.com/guusto/img/116.png");
	}
	
	public static Merchant createMerchantToBeSaved() {
		return new Merchant("Amazon", "Canada", "https://amazon.ca", "http://images.guusto.com/guusto/img/116.png");
	}
}
