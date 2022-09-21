using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addContractExpressCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "expressAddress",
                table: "contract",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "expressNo",
                table: "contract",
                maxLength: 20,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "expressAddress",
                table: "contract");

            migrationBuilder.DropColumn(
                name: "expressNo",
                table: "contract");
        }
    }
}
