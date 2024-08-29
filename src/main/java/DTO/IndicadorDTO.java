package DTO;

public class IndicadorDTO {
		
		private Long id;
		private String description;
		
		
		public IndicadorDTO(Long id, String description) {
			this.description = description;
			this.id = id;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}
		
		
}
