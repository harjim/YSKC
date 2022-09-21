using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_attachments : tablebase
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }

        [MaxLength(500)]
        public string filePath { get; set; }

        public int  settingId{ get; set; }

    }
}