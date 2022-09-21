using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{
    public class c_product_year : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int productId { get; set; }

        public int year { get; set; }

        ///<summary>
        /// 产量
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal output { get; set; }

        ///<summary>
        /// 产值
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal outputValue { get; set; }
    }
}
