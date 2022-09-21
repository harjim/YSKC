using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addColToPatentBuying : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "demandId",
                table: "patent_buying_list",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "patentSeaId",
                table: "patent_buying_list",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "patent_buying_list",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "demandId",
                table: "patent_buying_list");

            migrationBuilder.DropColumn(
                name: "patentSeaId",
                table: "patent_buying_list");

            migrationBuilder.DropColumn(
                name: "status",
                table: "patent_buying_list");
        }
    }
}
