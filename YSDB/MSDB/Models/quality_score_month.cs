using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class quality_score_month : tablebase
    {
        [Key]
        public int id { get; set; }

        public int  rsProjectId { get; set; }

        [MaxLength(500),Required]
        public string months { get; set; }

    }
}