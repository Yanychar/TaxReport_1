package com.c2point.tms.entity.taxreport;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.LocalDate;

public class Contract {

	private long 		id;
	
	public enum ContractType {
		Contract( 1 ), Leasing( 2 ), Service( 3 );

		private int value = -1;
		
		ContractType( int value ) {
			this.value = value;
			
		}
		
		public int value() {
			return this.value;
		}
		
		public String getName() {
			switch ( this ) {
				case Contract:
					return "Contracting";
				case Leasing:
					return "Leasing";
				case Service:
					return "Maintenance Service";
			}
			
			return "Unknown";
		}
	};
		
	private Contractor		contractor;
	
	
	//	Contract Details

	private ContractType	contractType;
	
	private Boolean	vatReverse;
	
	private Long	invoiced;	
	private Long	paid;
	private Long	advanced;
	private Long	total;
	
	private LocalDate	startDate;
	private LocalDate	endDate;
	

	public Contract() {
		
		setId( RandomUtils.nextLong());
		
		setContractType( ContractType.Contract );
		setVatReverse( true );
	}

	public Contract( Contractor contractor, ContractType contractType ) {
		
		this();
		
		setContractor( contractor );
		setContractType( contractType );

	}

	@XmlTransient
	public long getId() { return id; }
	public void setId( long id ) { this.id = id; }
	
	public Contractor getContractor() { return contractor; }
	public void setContractor( Contractor contractor ) { this.contractor = contractor; }

	@XmlElement( name = "ctype" )
	public ContractType getContractType() { return contractType; }
	public void setContractType(ContractType contractType) { this.contractType = contractType; }
	
	@XmlElement( name = "vat" )
	public Boolean isVatReverse() { return vatReverse; }
	public void setVatReverse( Boolean vatReverse) { this.vatReverse = vatReverse; }
	public void setVatReverse( boolean vatReverse) { this.vatReverse = vatReverse; }

	public Long getInvoiced() { return invoiced; }
	public void setInvoiced( Long invoiced ) { this.invoiced = invoiced; }
	public void setInvoiced( long invoiced ) { this.invoiced = invoiced; }

	public Long getPaid() { return paid; }
	public void setPaid( long paid ) { this.paid = paid; }
	public void setPaid( Long paid ) { this.paid = paid; }

	public Long getAdvanced() { return advanced; }
	public void setAdvanced( long advanced ) { this.advanced = advanced; }
	public void setAdvanced( Long advanced ) { this.advanced = advanced; }

	public Long getTotal() { return total; }
	public void setTotal( long total ) { this.total = total; }
	public void setTotal( Long total ) { this.total = total; }

	@XmlElement( name = "start" )
	@XmlJavaTypeAdapter( type=LocalDate.class, value=LocalDateXmlAdapter.class )
	public LocalDate getStartDate() { return startDate; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	public void setStartDate( Date startDate ) { 
		setStartDate( startDate != null ? new LocalDate( startDate ) : null ); 
	}

	@XmlElement( name = "end" )
	@XmlJavaTypeAdapter( type=LocalDate.class, value=LocalDateXmlAdapter.class )
	public LocalDate getEndDate() { return endDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
	public void setEndDate( Date endDate ) { 
		setEndDate( endDate != null ? new LocalDate( endDate ) : null ); 
	}
	
}
