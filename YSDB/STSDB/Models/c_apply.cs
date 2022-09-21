using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class c_apply : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }

        public int projectId { get; set; }

        [MaxLength(50)]
        public string stage { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
    }
}
