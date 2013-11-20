require 'rubygems'
require 'nokogiri'   
require 'open-uri'

# page = Nokogiri::HTML(open("http://www.smu.edu.sg/rps.iframe.server.php?page=1&ipp=300&rps_title=&rps_author=&rps_research%5B%5D=&pubtype_display%5B%5D=&rps_faculty_id%5B%5D=&rps_year_from=2012&rps_year_to=2012&domain=SIS&domain_query=+School%3D%27SIS%27+"))   

# for year in 2000..2013
year = 2013

url = "http://www.smu.edu.sg/rps.iframe.server.php?rps_title=&rps_author=&pubtype_display%5B%5D=&rps_faculty_id%5B%5D=&rps_school%5B%5D=&rps_year_from="+year.to_s+"&rps_year_to="+year.to_s+"&domain=&domain_query=&ipp=10000"
page = Nokogiri::HTML(open(url))   
records = page.css("table.output tr td.rps_single div")

pub_titles = []
researchers = []
schools = []

index = 0 
records.each do |record|
	if index % 2 == 0
		pub_titles << record.css("strong").text.strip
		schools << record.css("div.schooltag").text.strip

		record.at_css("div.schooltag").remove
		record.css("strong").remove
		
		researchers << record.inner_text.strip
	end
	index +=1
end


temp_researchers = []

researchers.each do |attributes|
	# puts attributes.to_s
	name = attributes.split(/\d+/).first

	name = name.split("by ").last
	name = name.split(", ")
	
	# puts name
	temp_researchers << name

end
# puts temp_researchers
i = 0
# out_file = File.new("publications&collaborators"+year.to_s+".csv", "w")
out_file = File.new("publications.csv", "a")

pub_titles.each do |title| 
	temp_researcher = temp_researchers[i]
	temp_school = schools[i]
	
	string_out = title.to_s + "|"

	# string_out = string_out + temp_school.to_s + "|"
	string_out = string_out + " |"
	

	temp_researcher.each do |name|
		if name != temp_researcher.last
			string_out = string_out + name.to_s + ","
		else
			string_out = string_out + name.to_s
		end
	end	

	string_out = string_out+ "|" + year.to_s 

	out_file.puts string_out
	i += 1
end