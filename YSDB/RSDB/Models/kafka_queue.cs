using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    public class kafka_queue
    {
        [Key]
        public int id { get; set; }
        [Required, MaxLength(50)]
        public string topic { get; set; }

        [Required, MaxLength(40)]
        public string key { get; set; }
        [Required, Column(TypeName = "text")]
        public string data { get; set; }
        public DateTime createTime { get; set; }
        public DateTime? lastUpdateTime { get; set; }
        ///<summary>
        /// 0 未消费，1已消费
        ///</summary>
        public byte status { get; set; }
    }
}