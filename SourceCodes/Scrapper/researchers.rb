require 'rubygems'
require 'nokogiri'   
require 'open-uri'

# expertise_ids = ["246","257","256","333","287","285","291","260","255","289","288","282","251","249","265","270","259","258","332","263","254","294",
				# "295","273","284","293","268","250","264","275","286","274","277","247","269","279","278","280","253","252","296","271","276","261","290","292","272","267","283","335","266","262","281"]

page_ids = ["0","1","2","3","4","5","6"]
page_ids.each do |page_id|

# expertise_ids.each do |expertise_id|

page = Nokogiri::HTML(open("http://www.smu.edu.sg/faculty?title=&lname=&school=All&field_competencies_tid=All&page="+ page_id))   

# page = Nokogiri::HTML(open("http://www.smu.edu.sg/faculty?title=&lname=&school=All&field_competencies_tid="+ expertise_id))   
initial = page.css("div.view-content")[0]

# profile_pics_array = initial.css("div.views-row div.views-field-php span a img")
# profile_pics_array = page.css("img[width=100]")
profile_pics_array = page.css("img[width='100']")
names_links = initial.css("div.views-row div.views-field-title a")
designations_array = initial.css("div.views-row div.views-field-field-profile-designation div")
qualifications_array = initial.css("div.views-row div.views-field-field-profile-qualification div")
faculty_array = initial.css("div.views-row div.views-field-php-2 span a")
emails_array = initial.css("div.views-row span.views-field-field-email span a")
phones_array = initial.css("div.views-row span.views-field-field-tel span.field-content")

profile_links = []
names = []
designations = []
qualifications = []
faculty_list = ["School of Economics", "School of Information Systems", "School of Law", "School of Accountancy", "School of Social Sciences", "Lee Kong Chian School of Business"]
faculties = []
emails = []
phones = []
profile_pics = []

puts profile_pics_array
profile_pics_array.each do |profile_pic| 

	profile_pics << profile_pic['src'].strip

end 


names_links.each do |link| 

	profile_links << "www.smu.edu.sg." + link['href']
	names << link.text.strip

end 

designations_array.each do |designation| 

	designations << designation.text.strip 	

end 

qualifications_array.each do |qualification| 

	qualifications << qualification.text.strip 	

end 


faculty_array.each do |faculty| 
	if faculty_list.include?(faculty.text.strip) # a professor might be associated with more than one school, then just choose the primary faculty
		faculties << faculty.text.strip 	
	end
end 


emails_array.each do |email| 

	emails << email.text.strip 	

end 

phones_array.each do |phone| 

	phones << phone.text.strip 	

end

puts names
out_file = File.new("researchers.csv", "a")

i = 0 


for i in 0..(names.size-1)

	string_out = names[i].to_s + "|" + profile_pics[i].to_s  + "|" +   profile_links[i].to_s  + "|" +  designations[i].to_s  +  "|" + qualifications[i].to_s  + "|" +   faculties[i].to_s  + "|" +  emails[i].to_s  + "|" +  phones[i].to_s + "|" + "0"
	# string_out = names[i].to_s + "|" + profile_pics[i].to_s  + "|" +   profile_links[i].to_s  + "|" +  designations[i].to_s  +  "|" + qualifications[i].to_s  + "|" +   faculties[i].to_s  + "|" +  emails[i].to_s  + "|" +  phones[i].to_s + "|" + expertise_id
	out_file.puts string_out
end


end

