
package com.demo.client;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.demo.domain.WriteEventLog;
import com.demo.fedex.domain.Address;
import com.demo.fedex.domain.CarrierCodeType;
import com.demo.fedex.domain.ClientDetail;
import com.demo.fedex.domain.CompletedTrackDetail;
import com.demo.fedex.domain.Localization;
import com.demo.fedex.domain.PagingDetail;
import com.demo.fedex.domain.TrackIdentifierType;
import com.demo.fedex.domain.TrackPackageIdentifier;
import com.demo.fedex.domain.TrackReply;
import com.demo.fedex.domain.TrackRequest;
import com.demo.fedex.domain.TrackSelectionDetail;
import com.demo.fedex.domain.TransactionDetail;
import com.demo.fedex.domain.VersionId;
import com.demo.fedex.domain.WebAuthenticationCredential;
import com.demo.fedex.domain.WebAuthenticationDetail;

@Component
public class FedexTrackerClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(FedexTrackerClient.class);

	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Value("${fedex.endpoint}")
	String endpoint;
	@Value("${fedex.key}")
	String key;
	@Value("${fedex.password}")
	String password;
	@Value("${fedex.accountNumber}")
	String accountNumber;
	@Value("${fedex.meternumber}")
	String meternumber;

	public TrackReply trackFedEx(TrackRequest request) {
		TrackReply response = null;
		WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		try {
			response = (TrackReply) webServiceTemplate.marshalSendAndReceive(endpoint, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public void printResponse(TrackReply response) {

		List<CompletedTrackDetail> forecastReturn = response.getCompletedTrackDetails();

		if (!forecastReturn.isEmpty()) {
			log.info("Result size " + forecastReturn.size());
			for (CompletedTrackDetail forecast : forecastReturn) {
				log.info("Result :  " + forecast.toString());
			}
		} else {
			log.info("No Response received");
		}
	}

	public TrackRequest createRequest(WriteEventLog writeEventLog) {
		TrackRequest request = new TrackRequest();
		WebAuthenticationDetail authenticationDetail = new WebAuthenticationDetail();
		WebAuthenticationCredential authenticationCredential = new WebAuthenticationCredential();
		authenticationCredential.setKey(key);
		authenticationCredential.setPassword(password);
		authenticationDetail.setUserCredential(authenticationCredential);
		request.setWebAuthenticationDetail(authenticationDetail);
		ClientDetail clientdetail = new ClientDetail();
		clientdetail.setAccountNumber(accountNumber);
		clientdetail.setMeterNumber(meternumber);
		clientdetail.setIntegratorId("123");
		Localization localization = new Localization();
		localization.setLanguageCode("EN");
		localization.setLocaleCode("US");
		clientdetail.setLocalization(localization);
		request.setClientDetail(clientdetail);
		TransactionDetail transactionDetail = new TransactionDetail();
		transactionDetail.setLocalization(localization);
		transactionDetail.setCustomerTransactionId(writeEventLog.getRequest().getInvoiceNo());
		request.setTransactionDetail(transactionDetail);
		VersionId versionId = new VersionId();
		versionId.setIntermediate(0);
		versionId.setMajor(12);
		versionId.setMinor(0);
		versionId.setServiceId("trck");
		request.setVersion(versionId);
		TrackSelectionDetail trackSelectionDetail = new TrackSelectionDetail();
		trackSelectionDetail.setCarrierCode(CarrierCodeType.FDXE);
		TrackPackageIdentifier trackPackageIdentifier = new TrackPackageIdentifier();
		trackPackageIdentifier.setType(TrackIdentifierType.TRACKING_NUMBER_OR_DOORTAG);
		trackPackageIdentifier.setValue(writeEventLog.getRequest().getTrackingNumber());
		trackSelectionDetail.setTrackingNumberUniqueIdentifier(writeEventLog.getRequest().getTrackingNumber());
		trackSelectionDetail.setPackageIdentifier(trackPackageIdentifier);
		trackSelectionDetail.setShipmentAccountNumber("510087020");
		trackSelectionDetail.setSecureSpodAccount("510051408");
		Address address = new Address();
		address.getStreetLines().add(writeEventLog.getRequest().getEventArrivalLocation());
		address.setCity(writeEventLog.getRequest().getEventCity());
		address.setStateOrProvinceCode(writeEventLog.getRequest().getEventState());
		address.setPostalCode(writeEventLog.getRequest().getEventZip());
		address.setCountryCode(writeEventLog.getRequest().getEventCountry());
		trackSelectionDetail.setDestination(address);
		PagingDetail pagingDetail = new PagingDetail();
		pagingDetail.setNumberOfResultsPerPage(new BigInteger("100"));
		pagingDetail.setPagingToken("100");
		trackSelectionDetail.setPagingDetail(pagingDetail);
		request.getSelectionDetails().add(trackSelectionDetail);
		return request;
	}
}
