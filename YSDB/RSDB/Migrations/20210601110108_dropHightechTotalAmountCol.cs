using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dropHightechTotalAmountCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "totalAmount",
                table: "highTech");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "totalAmount",
                table: "highTech",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
