using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class alterCompanyListedTolistTed : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Listed",
                table: "company",
                newName: "listed");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "listed",
                table: "company",
                newName: "Listed");
        }
    }
}
