using System;
using System.ComponentModel.DataAnnotations; 
namespace MSDB.Models
{
    public class policySourceUser
    {

        [Key]
        public int id { get; set; }
        public int sourceId { get; set; }
        public int userId { get; set; }
        public DateTime createTime { get; set; }
    }
}