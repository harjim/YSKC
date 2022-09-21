using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterRdInsuranceFundCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "insuranceFund",
                table: "p_rdEmployee",
                newName: "rdInsuranceFund");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdInsuranceFund",
                table: "p_rdEmployee",
                newName: "insuranceFund");
        }
    }
}
