package codechallenge.gustavohmcaldas.guustocustomers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Service;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;
import codechallenge.gustavohmcaldas.guustocustomers.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DBService {
	
	private static final String WEBSITE = "https://guusto.com/merchants";
	private static final String USA = "USA";
	private static final String CANADA = "Canada";
	
	private final MerchantRepository merchantRepository;
	
	public void dataBaseInstance() {
		try {
			scanItems();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scanItems() throws IOException {
		
		Document doc = Jsoup.connect(WEBSITE).get();

		Element merchantsTable = doc.getElementById("USA-merchants");
		getMerchants(merchantsTable, USA);
		
		merchantsTable = doc.getElementById("CAN-merchants");
		getMerchants(merchantsTable, CANADA);
	}

	private void getMerchants(Element merchantsTable, String country) {
		List<Element> elements = merchantsTable.getElementsByClass("row align-items-center h-100");
		
		List<Merchant> merchantList = new ArrayList<>();
		
		for (Element element : elements) {
			String merchantName = Parser.unescapeEntities(element.getElementsByClass("merchant-name font-weight-bold").get(0).childNode(0).toString(), false);
			String merchantWebsite = element.getElementsByClass("merchant-website").get(0).childNode(0).attributes().get("href");
			String imageUrl = element.getElementsByClass("d-block").get(0).attributes().get("src");
						
			Merchant merchant = new Merchant(merchantName, country, merchantWebsite, imageUrl);
			merchantList.add(merchant);
		}
		
		merchantRepository.saveAll(merchantList);
	}
}
