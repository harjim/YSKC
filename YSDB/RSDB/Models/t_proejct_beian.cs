using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    
    /// <summary>
    /// 技改-备案关联表
    /// </summary>
    public class t_project_beian : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int beianId { get; set; }
        public int projectId { get; set; }
    }
}